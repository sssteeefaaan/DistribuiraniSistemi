#include <stdio.h>
#include <mpi.h>
#include <math.h>

#define n 10
int main(int argc, char** argv)
{
	int rank, size;

	MPI_Init(&argc, &argv);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
	MPI_Comm_size(MPI_COMM_WORLD, &size);

	int mat[n][n], b[n], local_row[n], result[n];
	if (!rank)
	{
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				mat[i][j] = (i + j)%10;
	}

	MPI_Scatter(mat, n, MPI_INT, local_row, n, MPI_INT, 0, MPI_COMM_WORLD);

	if (!rank){
		for (int i = 0; i < n; i++)
			b[i] = (i * 2)%10;
	}

	MPI_Bcast(b, n, MPI_INT, 0, MPI_COMM_WORLD);

	int part_vec = 0;
	for (int i = 0; i < n; i++)
		part_vec+= local_row[i] * b[i];

	MPI_Gather(&part_vec, 1, MPI_INT, result, 1, MPI_INT, 0, MPI_COMM_WORLD);

	if (!rank) {
		printf("Matrica:\n");
		for (int i = 0; i < n; i++)
		{
			printf("|\t");
			for (int j = 0; j < n; j++)
				printf("%d\t", mat[i][j]);
			printf("|\n");
		}

		printf("Vektor:\n[");
		for (int i = 0; i < n; i++) printf("%d\t", b[i]);

		printf("]\nProizvod:\n");
		for (int i = 0; i < n; i++)
			printf("%d\t", result[i]);
		printf("]\n");
	}

	MPI_Finalize();
	return 0;
}