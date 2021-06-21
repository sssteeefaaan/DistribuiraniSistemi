#include <stdio.h>
#include <mpi.h>

int main(int argc, char** argv)
{
	int rank, size;

	MPI_Init(&argc, &argv);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
	MPI_Comm_size(MPI_COMM_WORLD, &size);

	int n = size * (size - 1);
	int* vrednosti = new int[n];

	MPI_Status status;

	if (!rank) {
		for (int i = 0; i < n; i++)
			vrednosti[i] = (i + 1);

		for (int i = 1; i < size; i++)
			MPI_Send(&vrednosti[2 * (i-1)], 2 *i, MPI_INT, i, 0, MPI_COMM_WORLD);
	}
	else {
		int no_el = rank * 2;
		int* recvBuff = new int[no_el];

		MPI_Recv(&recvBuff[0], no_el, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);

		int suma = 0;
		for (int i = 0; i < no_el; i++)
			suma += recvBuff[i];

		if (recvBuff)delete[] recvBuff;

		printf("[%d] Suma mog dela iznosi: %d", rank, suma);
	}

	if (vrednosti)delete[] vrednosti;

	MPI_Finalize();
	return 0;
}