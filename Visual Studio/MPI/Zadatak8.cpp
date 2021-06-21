//#include <mpi.h>
//#include <stdio.h>
//#include <stdlib.h>
//
//int main(int argc, char** argv)
//{
//	int rank,
//		size,
//		root = 0,
//		n,
//		* row = nullptr,
//		* vector = nullptr,
//		* matrix = nullptr,
//		* rez = nullptr,
//		partial;
//
//	/*MPI_Status status;
//	MPI_Request request;*/
//
//	MPI_Init(&argc, &argv);
//	MPI_Comm_size(MPI_COMM_WORLD, &size);
//	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
//
//	/*if (rank == root)
//	{
//		printf("Unesite n: ");
//		fflush(stdout);
//		scanf_s("%d", &n);
//	}
//
//	MPI_Bcast(&n, 1, MPI_INT, root, MPI_COMM_WORLD);*/
//
//	n = size;
//
//	vector = (int*)malloc(sizeof(int) * n);
//
//	if (rank == root)
//	{
//		printf("Unesite elemente bnx1: ");
//		fflush(stdout);
//		for(int i = 0; i < n; i++)
//			scanf_s("%d", &vector[i]);
//	}
//
//	MPI_Bcast(vector, n, MPI_INT, root, MPI_COMM_WORLD);
//
//	if (rank == root)
//	{
//		matrix = (int*) malloc(n * n * sizeof(int));
//		printf("Unesite elemente matrice Anxn\n");
//		for (int i = 0; i < n; i++)
//		{
//			printf("Unesite elemente %d. vrste matrice: ", i + 1);
//			fflush(stdout);
//			for (int j = 0; j < n; j++)
//				scanf_s("%d", &matrix[i * n + j]);
//		}
//	}
//
//	row = (int*)malloc(sizeof(int) * n);
//	/*if (rank == root)
//		for (int i = 0; i < n; i++)
//			MPI_Isend(matrix[i], n, MPI_INT, i, 0, MPI_COMM_WORLD, &request);
//
//	MPI_Recv(row, n, MPI_INT, root, 0, MPI_COMM_WORLD, &status);*/
//
//	MPI_Scatter(matrix, n, MPI_INT, row, n, MPI_INT, root, MPI_COMM_WORLD);
//
//	printf("Proccess %d: [ ", rank);
//	for (int i = 0; i < n; i++)
//		printf("%d ", row[i]);
//	printf("]\n");
//	fflush(stdout);
//
//	partial = 0;
//	for (int i = 0; i < n; i++)
//		partial += row[i] * vector[i];
//
//	if(rank == root)
//		rez = (int*)malloc(sizeof(int) * n);
//
//	MPI_Gather(&partial, 1, MPI_INT, rez, 1, MPI_INT, root, MPI_COMM_WORLD);
//
//	if (rank == root)
//	{
//		printf("Rezultat proizvoda Anxn i bnx1 je: [ ");
//		for (int i = 0; i < n; i++)
//			printf("%d ", rez[i]);
//		printf("]\n");
//		fflush(stdout);
//	}
//
//	free(vector);
//	free(row);
//	free(rez);
//	free(matrix);
//
//	MPI_Finalize();
//
//	return 0;
//}