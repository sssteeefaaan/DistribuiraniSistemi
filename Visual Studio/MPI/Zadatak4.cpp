//#include <mpi.h>
//#include <stdio.h>
//#include <math.h>
//
//int main(int argc, char** argv)
//{
//	int rank, size, root = 0;
//
//	struct data {
//		int val;
//		int rank;
//	} send, recv;
//
//	MPI_Init(&argc, &argv);
//	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
//	MPI_Comm_size(MPI_COMM_WORLD, &size);
//
//	send.val = (size << rank) ^ (int)pow(size, rank) & ((1 << 16) - 1);
//	send.rank = rank;
//
//	printf("[Proccess %d]: %d\n", send.rank, send.val);
//
//	MPI_Reduce(&send, &recv, 1, MPI_2INT, MPI_MINLOC, root, MPI_COMM_WORLD);
//
//	if (rank == root)
// {
//		printf("Proces koji ima najmanju vrednost je sa ID-em {%d}, a vrednost je {%d}!\n", recv.rank, recv.val);
//		fflush(stdout);
// }
//
//	MPI_Reduce(&send, &recv, 1, MPI_2INT, MPI_MAXLOC, root, MPI_COMM_WORLD);
//
//	if (rank == root)
// {
//		printf("Proces koji ima najvecu vrednost je sa ID-em {%d}, a vrednost je {%d}!\n", recv.rank, recv.val);
//		fflush(stdout);
// }
//
//	MPI_Finalize();
//	return 0;
//}