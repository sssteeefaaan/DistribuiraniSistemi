//#include <mpi.h>
//#include <stdio.h>
//#include <stdlib.h>
//
//int main(int argc, char** argv)
//{
//	int rank, size, val, n, * podaci, *stepenovani, temp, fin;
//
//	MPI_Init(&argc, &argv);
//	MPI_Comm_size(MPI_COMM_WORLD, &size);
//	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
//
//	if (rank == 0)
//	{
//		printf("Unesite vrednost: ");
//		fflush(stdout);
//		scanf_s("%d", &val);
//	}
//
//	MPI_Bcast(&val, 1, MPI_INT, 0, MPI_COMM_WORLD);
//
//	printf("Proces [%d] val = %d", rank, val);
//	fflush(stdout);
//
//	if (rank == 0)
//	{
//		printf("Unesite broj podataka za slanje: ");
//		fflush(stdout);
//		scanf_s("%d", &n);
//	}
//
//	MPI_Bcast(&n, 1, MPI_INT, 0, MPI_COMM_WORLD);
//	podaci = (int*)malloc(sizeof(int) * n);
//
//	if (rank == 0)
//	{
//		printf("Unesite podatke: ");
//		fflush(stdout);
//		for (int i = 0; i < n; i++)
//			scanf_s("%d", &podaci[i]);
//	}
//
//	MPI_Bcast(podaci, n, MPI_INT, 0, MPI_COMM_WORLD);
//
//	stepenovani = (int*)malloc(sizeof(int) * n);
//	MPI_Reduce(podaci, stepenovani, n, MPI_INT, MPI_PROD, 0, MPI_COMM_WORLD);
//
//	if (rank == 0)
//	{
//		printf("Stepenovani podaci: [ ");
//		for (int i = 0; i < n; i++)
//			printf("%d ", stepenovani[i]);
//		printf("]\n");
//		fflush(stdout);
//	}
//
//	temp = 1;
//	for (int i = rank; i < n; i += size)
//		temp *= size * podaci[i];
//
//	MPI_Reduce(&temp, &fin, 1, MPI_INT, MPI_PROD, 0, MPI_COMM_WORLD);
//
//	if (rank == 0)
//	{
//		printf("Proizvod svih parcijalnih proizvoda size*xn: %d.\n", fin);
//		fflush(stdout);
//	}
//
//	free(stepenovani);
//	free(podaci);
//
//	MPI_Finalize();
//	return 0;
//}