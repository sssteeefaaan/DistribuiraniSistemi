//#include <stdio.h>
//#include <mpi.h>
//#include <string>
//using namespace std;
//
//int main(int argc, char** argv)
//{
//	int size, rank;
//
//	MPI_Init(&argc, &argv);
//	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
//	MPI_Comm_size(MPI_COMM_WORLD, &size);
//
//	int val;
//	MPI_Status status;
//
//	while (true)
//	{
//		if (rank == 0)
//		{
//			printf("Unesite pozitivan broj za preneti, ili negativan za kraj: ");
//			fflush(stdout);
//			scanf_s("%d", &val);
//			MPI_Send(&val, 1, MPI_INT, rank + 1, 0, MPI_COMM_WORLD);
//		}
//		else
//		{
//			MPI_Recv(&val, 1, MPI_INT, rank - 1, 0, MPI_COMM_WORLD, &status);
//			if (rank < size - 1)
//				MPI_Send(&val, 1, MPI_INT, rank + 1, 0, MPI_COMM_WORLD);
//		}
//
//		printf("[Proccess %d]: %d\n", rank, val);
//		fflush(stdout);
//
//		if (val < 0)
//			break;
//	}
//
//	MPI_Finalize();
//	return 0;
//}