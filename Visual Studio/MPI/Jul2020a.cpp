//#include <mpi.h>
//#include <stdio.h>
//#include <stdlib.h>
//#include <math.h>
//
//int main(int argc, char** argv)
//{
//	int rank, size, val;
//
//	MPI_Status status;
//
//	MPI_Init(&argc, &argv);
//	MPI_Comm_size(MPI_COMM_WORLD, &size);
//	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
//
//	if (rank == 0)
//		val = 5;
//
//	int levels = log2(size);
//
//	for (int i = 0; i < levels; i++)
//	{
//		for (int j = 0; j < 1 << i; j++)
//		{
//			if (rank == j)
//			{
//				MPI_Send(&val, 1, MPI_INT, j + (1 << i), 0, MPI_COMM_WORLD);
//				/*printf("Process [%d] poslao procesu %d.\n", rank, j + (1 << i));
//				fflush(stdout);*/
//			}
//			else if (rank == j + (1 << i))
//			{
//				MPI_Recv(&val, 1, MPI_INT, j, 0, MPI_COMM_WORLD, &status);
//				printf("Process [%d] primio od procesa %d.\n", rank, j);
//				fflush(stdout);
//			}
//		}
//	}
//
//	printf("Process [%d] holds %d.\n", rank, val);
//	//fflush(stdout);
//
//	MPI_Finalize();
//	return 0;
//}