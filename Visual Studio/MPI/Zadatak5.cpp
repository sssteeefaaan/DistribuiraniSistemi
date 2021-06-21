//#include <mpi.h>
//#include <stdio.h>
//#include <math.h>
//
//int main(int argc, char** argv)
//{
//	int root = 0, rank, size, val;
//
//	struct {
//		double val;
//		int rank;
//	} in[30], out[30];
//
//	MPI_Init(&argc, &argv);
//	MPI_Comm_size(MPI_COMM_WORLD, &size);
//	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
//
//	val = (int)pow(size, rank) ^ (size << rank) & ((1 << 16) - 1);
//
//	for (int i = 0; i < 30; i++)
//	{
//		in[i].val = (double)val / (i + 1);
//		in[i].rank = rank;
//	}
//
//	MPI_Reduce(&in, &out, 30, MPI_DOUBLE_INT, MPI_MAXLOC, root, MPI_COMM_WORLD);
//
//	if (rank == root)
//	{
//		printf("Par (proces, vrednosti) za svaki od 30 elemenata:\n");
//		for (int i = 0; i < 30; i++)
//			printf("\t%d => (%d, %f)\n", i, out[i].rank, out[i].val);
// 
//		fflush(stdout);
//	}
//
//	MPI_Finalize();
//	return 0;
//}