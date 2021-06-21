#include <stdio.h>
#include <iostream>
#include <bitset>
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

	int recv;
	MPI_Status status;

	if (!rank)
	{
		MPI_Recv(&recv, 1, MPI_INT, rank + 1, 0, MPI_COMM_WORLD, &status);
		for (int i = 1; i < size; i++) {
			if (recv & 1)
				printf("Proces [%d] ima parnu sumu.\n", i);
			recv >>= 1;
		}
	}
	else {

		int* buffer = new int[n];
		for (int i = 0; i < n; i++)
			buffer[i] = rank * i;

		int suma = 0;
		for (int i = 0; i < n; i++)
			suma += buffer[i];

		printf("[%d] Suma = %d\n", rank, suma);
		if (buffer) delete[] buffer;

		int pod = 0;
		if (!(suma % 2))
			pod ^= 1;

		if (rank == size - 1)
			MPI_Send(&pod, 1, MPI_INT, rank - 1, 0, MPI_COMM_WORLD);
		else {
			MPI_Recv(&recv, 1, MPI_INT, rank + 1, 0, MPI_COMM_WORLD, &status);
			recv <<= 1;
			pod |= recv;
			MPI_Send(&pod, 1, MPI_INT, rank - 1, 0, MPI_COMM_WORLD);
		}

		printf("[%d] Indikator(int) = %s\n", rank, std::bitset<16>(pod).to_string().c_str());
	}


	MPI_Finalize();
	return 0;
}