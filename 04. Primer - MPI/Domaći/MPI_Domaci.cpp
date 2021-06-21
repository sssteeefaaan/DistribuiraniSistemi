#include <mpi.h>
#include <stdio.h>
#include <math.h>

int main(int argc, char** argv)
{
	int myrank, size, levels, sum, buff, temp;

	MPI_Status status;

	MPI_Init(&argc, &argv);
	MPI_Comm_rank(MPI_COMM_WORLD, &myrank);
	MPI_Comm_size(MPI_COMM_WORLD, &size);

	levels = log2(size);
	sum = myrank;
	
	for (int i = 0; i < levels; i++)
	{
		temp = (int)pow(2, i + 1);
		if (myrank %  temp == 0)
		{
			MPI_Recv(&buff, 1, MPI_INT, myrank + temp / 2, 2, MPI_COMM_WORLD, &status);
			sum += buff;
		}
		else if (myrank % temp == temp / 2) {
			MPI_Send(&sum, 1, MPI_INT, myrank - temp / 2, 2, MPI_COMM_WORLD);
			break;
		}
	}

	if (myrank == 0)
		printf("Suma iznosi: %d\n", sum);

	MPI_Finalize();
	return 0;
}