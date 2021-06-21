#include <stdio.h>
#include <mpi.h>

int main(int argc, char** argv)
{
	int rank, size;
	MPI_Init(&argc, &argv);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
	MPI_Comm_size(MPI_COMM_WORLD, &size);

	MPI_Status status;
	double send_time;

	if (!rank) {

		for (int i = 1; i < size; i++) {
			send_time = MPI_Wtime();
			MPI_Send(&send_time, 1, MPI_DOUBLE, i, 0, MPI_COMM_WORLD);
		}

		for (int i = 1; i < size; i++) {
			MPI_Recv(&send_time, 1, MPI_DOUBLE, i, 0, MPI_COMM_WORLD, &status);
			printf("[%d] Primeljna blokirajuca poruka od procesa [%d]: %fs\n", rank, i, MPI_Wtime() - send_time);
		}

		MPI_Request* requests = new MPI_Request[size - 1];

		for (int i = 1; i < size; i++) {
			send_time = MPI_Wtime();
			MPI_Isend(&send_time, 1, MPI_DOUBLE, i, 0, MPI_COMM_WORLD, &requests[i - 1]);
			MPI_Wait(&requests[i - 1], &status);
		}

		for (int i = 1; i < size; i++) {
			MPI_Irecv(&send_time, 1, MPI_DOUBLE, i, 0, MPI_COMM_WORLD, &requests[i - 1]);
			MPI_Wait(&requests[i - 1], &status);
			printf("[%d] Primeljna neblokirajuca poruka od procesa [%d]: %fs\n", rank, i, MPI_Wtime() - send_time);
		}

		if (requests)delete[] requests;
		printf("\n\n");
	}
	else
	{
		MPI_Recv(&send_time, 1, MPI_DOUBLE, 0, 0, MPI_COMM_WORLD, &status);
		printf("[%d] Primljena blokirajuca poruka od master procesa: %fs\n", rank, MPI_Wtime() - send_time);

		send_time = MPI_Wtime();
		MPI_Send(&send_time, 1, MPI_DOUBLE, 0, 0, MPI_COMM_WORLD);

		MPI_Request request;

		MPI_Irecv(&send_time, 1, MPI_DOUBLE, 0, 0, MPI_COMM_WORLD, &request);
		MPI_Wait(&request, &status);
		printf("[%d] Primljena neblokirajuca poruka od master procesa: %fs\n\n\n", rank, MPI_Wtime() - send_time);

		send_time = MPI_Wtime();
		MPI_Isend(&send_time, 1, MPI_DOUBLE, 0, 0, MPI_COMM_WORLD, &request);
		MPI_Wait(&request, &status);
	}

	MPI_Finalize();
	return 0;
}