//#include <mpi.h>
//#include <stdio.h>
//#include <math.h>
//
//double get_PI();
//int main(int argc, char** argv)
//{
//	int root = 0, rank, size, n = 65000;
//	double dx, sum = 0, rez, xi;
//
//	MPI_Init(&argc, &argv);
//	MPI_Comm_size(MPI_COMM_WORLD, &size);
//	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
//
//	dx = (double) 1 / n;
//	sum = 0.0;
//	for (int i = rank; i < n; i += size)
//	{
//		xi = dx * (0.5 + i);
//		sum += 4.0 / (1.0 + xi * xi);
//	}
//	sum *= dx;
//
//	MPI_Reduce(&sum, &rez, 1, MPI_DOUBLE, MPI_SUM, root, MPI_COMM_WORLD);
//
//	if (rank == root)
// {
//		printf("Vrednost iznosi: %.16f\nGreska je: %.16f\n", rez, fabs(get_PI() - rez));
//		fflush(stdout);
// }
//
//	MPI_Finalize();
//	return 0;
//}
//
//double get_PI()
//{
//	double pi;
//	__asm
//	{
//		fldpi
//		fstp pi
//	}
//	return pi;
//}