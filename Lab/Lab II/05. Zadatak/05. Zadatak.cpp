#include <stdio.h>
#include <mpi.h>

int main(int argc, char** argv)
{
	int rank, size;
	MPI_Init(&argc, &argv);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
	MPI_Comm_size(MPI_COMM_WORLD, &size);

	int n;
	if (!rank)
		scanf_s("%d", &n);

	MPI_Bcast(&n, 1, MPI_INT, 0, MPI_COMM_WORLD);

	bool indicator;

	if (rank) {
		int* niz = new int[n];

		for (int i = 0; i < n; i++)
			niz[i] = 2 * rank + i;

		int suma = 0;
		for (int i = 0; i < n; i++)
			suma += niz[i];

		printf("[%d] Moja suma iznosi %d\n", rank, suma);

		indicator = !(suma % rank);
		MPI_Send(&indicator, 1, MPI_C_BOOL, 0, 0, MPI_COMM_WORLD);

		if (niz)delete[] niz;
	}
	else {
		MPI_Status status;

		for (int i = 1; i < size; i++)
		{
			MPI_Recv(&indicator, 1, MPI_C_BOOL, i, 0, MPI_COMM_WORLD, &status);
			if (indicator)
				printf("[%d] Suma niza elemenata procesa [%d] je deljiva njegovim rankom.\n", rank, i);
			else
				printf("[%d] Suma niza elemenata procesa [%d] nije deljiva njegovim rankom.\n", rank, i);
		}
	}

	MPI_Finalize();
	return 0;
}