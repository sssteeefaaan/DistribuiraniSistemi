//#include <mpi.h>
//#include <stdio.h>
//#include <math.h>
//using namespace std;
//
//int main(int argc, char** argv)
//{
//	int rank, size, sum, levels, temp, acc;
//	MPI_Status status;
//
//	MPI_Init(&argc, &argv);
//	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
//	MPI_Comm_size(MPI_COMM_WORLD, &size);
//
//	/*acc = 0;
//	levels = size;
//	while (levels >>= 1 > 0)
//		acc++;
//	levels = acc;*/
//	levels = log2(size);
//	sum = rank;// +1;
//
//	for (int i = 0; i < levels; i++)
//	{
//		if (rank % (2 << i) != 0)
//		{
//			MPI_Send(&sum, 1, MPI_INT, rank - (1 << i), 1, MPI_COMM_WORLD);
//			break;
//		}
//		else {
//			MPI_Recv(&acc, 1, MPI_INT, rank + (1 << i), 1, MPI_COMM_WORLD, &status);
//			sum += acc;
//		}
//	}
//	
//	if (rank == 0)
// {
//		printf("Suma iznosi: %d\n", sum);
//		fflush(stdout);
// }
//
//	MPI_Finalize();
//	return 0;
//}