//#include <mpi.h>
//#include <stdio.h>
//
//int main(int argc, char** argv)
//{
//	int rank, size;
//
//	MPI_Init(&argc, &argv);
//	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
//	MPI_Comm_size(MPI_COMM_WORLD, &size);
//
//	int sum;
//	MPI_Status status;
//	if (rank == 0)
//	{
//		sum = 1;
//		MPI_Send(&sum, 1, MPI_INT, rank + 1, 0, MPI_COMM_WORLD);
//	}
//	else {
//		MPI_Recv(&sum, 1, MPI_INT, rank - 1, 0, MPI_COMM_WORLD, &status);
//		sum += rank + 1;
//		if (rank < size - 1)
//			MPI_Send(&sum, 1, MPI_INT, rank + 1, 0, MPI_COMM_WORLD);
//	}
//
//	if (rank == size - 1)
//		printf("Suma prvih N prirodnih brojeva, gde je N = %d, iznosi: %d\n", size, sum);
//
//	MPI_Finalize();
//	return 0;
//}