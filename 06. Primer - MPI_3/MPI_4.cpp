#include <stdio.h>
#include <mpi.h>
#include <math.h>

#define n 3
#define m 5

int main(int argc, char** argv)
{
	int rank, size;

	MPI_Init(&argc, &argv);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
	MPI_Comm_size(MPI_COMM_WORLD, &size);

	int mat[m][n], *b = nullptr, local_row[m], local_b, local_c[m], min = INT16_MAX, c[m];
	MPI_Status status;
	MPI_Request request;
	if (!rank)
	{
		for (int j = n - 1; j>= 0; j--) {
			for (int i = 0; i < m;i++) {
				mat[i][j] = ((i + j) % 10) * pow(-1, (i + j) / 10);
				local_row[i] = mat[i][j];
			}
			if (j)
				MPI_Send(local_row, m, MPI_INT, j, 0, MPI_COMM_WORLD);
		}
	}
	else {
		MPI_Recv(local_row, m, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);
	}

	if (!rank) {
		b = new int[n];
		for (int i = 0; i < n; i++)
			b[i] = 1;
	}

	MPI_Scatter(b, 1, MPI_INT, &local_b, 1, MPI_INT, 0, MPI_COMM_WORLD);

	for (int i = 0; i < m; i++) {
		local_c[i] = local_row[i] * local_b;
		if (min > local_row[i])
			min = local_row[i];
	}

	struct { int val; int rank; }findMinEl, res;
	findMinEl.val = min;
	findMinEl.rank = rank;

	MPI_Reduce(&findMinEl, &res, 1, MPI_2INT, MPI_MINLOC, 0, MPI_COMM_WORLD);
	MPI_Bcast(&res.rank, 1, MPI_INT, 0, MPI_COMM_WORLD);

	MPI_Reduce(local_c, c, m, MPI_INT, MPI_SUM, res.rank, MPI_COMM_WORLD);

	if (!rank)
	{
		printf("Matrica:\n");
		for (int i = 0; i < m; i++)
		{
			printf("|\t");
			for (int j = 0; j < n; j++)printf("%d\t", mat[i][j]);
			printf("|\n");
		}

		printf("Vektor:\n");
		for (int i = 0; i < n; i++)printf("|\t%d\t|\n", b[i]);
	}

	if (rank == res.rank) {
		printf("Rezultat:\n");
		for (int i = 0; i < m; i++)printf("|\t%d\t|\n", c[i]);
	}
	
	if (b != nullptr) {
		delete[] b;
		b = nullptr;
	}

	MPI_Finalize();
	return 0;
}