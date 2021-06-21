//#include <stdio.h>
//#include <mpi.h>
//
//int main(int argc, char** argv)
//{
//	int rank, size;
//	int rcvBuff;
//	int poruke[2] = { 111, 222 };
//	MPI_Status status;
//
//	MPI_Init(&argc, &argv);
//	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
//	MPI_Comm_size(MPI_COMM_WORLD, &size);
//
//	if (!rank)
//	{
//		MPI_Request request1, request2;
//		for (int i = 1; i < size; i++)
//		{
//			MPI_Isend(&poruke[0], 1, MPI_INT, i, 1, MPI_COMM_WORLD, &request1);
//			MPI_Isend(&poruke[1], 1, MPI_INT, i, 2, MPI_COMM_WORLD, &request2);
//
//			MPI_Wait(&request1, &status);
//			MPI_Wait(&request2, &status);
//		}
//	}
//	else {
//		MPI_Recv(&rcvBuff, 1, MPI_INT, 0, 2, MPI_COMM_WORLD, &status);
//		printf("[%d] Primio sam broj: %d\n", rank, rcvBuff);
//
//		MPI_Recv(&rcvBuff, 1, MPI_INT, 0, 1, MPI_COMM_WORLD, &status);
//		printf("[%d] Primio sam broj: %d\n", rank, rcvBuff);
//
//		MPI_Send(&rank, 1, MPI_INT, 0, 0, MPI_COMM_WORLD);
//	}
//
//	if (!rank)
//	{
//		for (int i = 1; i < size; i++)
//		{
//			MPI_Recv(&rcvBuff, 1, MPI_INT, i, 0, MPI_COMM_WORLD, &status);
//			printf("[%d] Proces {%d} je zavrsio!\n", rank, rcvBuff);
//		}
//	}
//
//	MPI_Finalize();
//	return 0;
//}