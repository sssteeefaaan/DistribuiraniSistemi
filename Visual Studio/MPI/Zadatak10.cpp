//#include <stdio.h>
//#include <mpi.h>
//#include <stdlib.h>
//
//int main(int argc, char** argv)
//{
//	int rank,
//		size,
//		root = 0,
//		m, 
//		n,
//		* matrix = nullptr, 
//		* vector = nullptr,
//		* row = nullptr,
//		* rez = nullptr,
//		* part = nullptr,
//		bi;
//
//	MPI_Status status;
//	MPI_Request request;
//
//	struct {
//		int val;
//		int rank;
//	}in, out;
//
//	MPI_Init(&argc, &argv);
//	MPI_Comm_size(MPI_COMM_WORLD, &size);
//	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
//
//	if (rank == root)
//	{
//		printf("Unesite m i n: ");
//		fflush(stdout);
//		scanf_s("%d %d", &m, &n);
//	}
//
//	MPI_Bcast(&m, 1, MPI_INT, root, MPI_COMM_WORLD);
//	MPI_Bcast(&n, 1, MPI_INT, root, MPI_COMM_WORLD);
//
//	if (rank == root)
//	{
//		vector = (int*)malloc(sizeof(int) * n);
//		printf("Unesite elemente vektora: ");
//		fflush(stdout);
//		for (int i = 0; i < n; i++)
//			scanf_s("%d", &vector[i]);
//	}
//
//	MPI_Scatter(vector, 1, MPI_INT, &bi, 1, MPI_INT, root, MPI_COMM_WORLD);
//
//	row = (int*)malloc(sizeof(int) * m);
//
//	if (rank == root)
//	{
//		matrix = (int*)malloc(sizeof(int) * m * n);
//		printf("Unesite matricu\n");
//		for (int i = 0; i < m; i++)
//		{
//			printf("Unesite elemente %d. vrste matrice: ", i + 1);
//			fflush(stdout);
//			for (int j = 0; j < n; j++)
//				scanf_s("%d", &matrix[i * n + j]);
//		}
//
//		for (int j = 0; j < m; j++)
//			row[j] = matrix[j * n];
//
//		for (int i = 1; i < n; i++)
//			for(int j = 0; j < m; j++)
//				MPI_Send(&matrix[j * n + i], 1, MPI_INT, i, 0, MPI_COMM_WORLD);
//	}
//	else
//		for(int i = 0; i < m; i++)
//			MPI_Recv(&row[i], 1, MPI_INT, root, 0, MPI_COMM_WORLD, &status);
//
//	printf("Proccess [%d] [", rank);
//	for (int i = 0; i < m; i++)
//		printf("%d ", row[i]);
//	printf("]\n");
//
//	fflush(stdout);
//
//	part = (int*)malloc(sizeof(int) * m);
//	in.val = INT16_MAX;
//	in.rank = rank;
//	for (int i = 0; i < m; i++)
//	{
//		part[i] = bi * row[i];
//		if (in.val > row[i])
//			in.val = row[i];
//	}
//
//	MPI_Reduce(&in, &out, 1, MPI_2INT, MPI_MINLOC, root, MPI_COMM_WORLD);
//	MPI_Bcast(&out, 1, MPI_2INT, root, MPI_COMM_WORLD);
//
//	rez = (int*)malloc(sizeof(int) * m);
//
//	MPI_Reduce(part, rez, m, MPI_INT, MPI_SUM, out.rank, MPI_COMM_WORLD);
//
//	if (rank == out.rank)
//	{
//		printf("Proizvod matrice i vektora je: [ ");
//		for (int i = 0; i < m; i++)
//			printf("%d ", rez[i]);
//		printf("]\n");
//
//		printf("Proccess [%d] sadrzi kolonu sa minimumom matrice => %d.\n", out.rank, out.val);
//		fflush(stdout);
//	}
//
//	free(vector);
//	free(matrix);
//	free(row);
//	free(part);
//	free(rez);
//
//	MPI_Finalize();
//	return 0;
//}