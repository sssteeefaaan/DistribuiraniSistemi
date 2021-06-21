//#include <mpi.h>
//#include <stdio.h>
//#include <stdlib.h>
//
//int main(int argc, char** argv)
//{
//	int rank,
//		size,
//		m,
//		* a = nullptr,
//		* b = nullptr,
//		* local_a = nullptr,
//		* local_b = nullptr,
//		local_size,
//		skalar,
//		local_skalar,
//		root = 0;
//
//	MPI_Status status;
//
//	MPI_Init(&argc, &argv);
//	MPI_Comm_size(MPI_COMM_WORLD, &size);
//	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
//
//	if (rank == root)
//	{
//		printf("Unesite m: ");
//		fflush(stdout);
//		scanf_s("%d", &m);
//
//		a = (int*)malloc(sizeof(int) * m);
//		b = (int*)malloc(sizeof(int) * m);
//
//		printf("Unesite vektor a: ");
//		fflush(stdout);
//		for (int i = 0; i < m; i++)
//			scanf_s("%d", &a[i]);
//
//		printf("Unesite vektor b: ");
//		fflush(stdout);
//		for (int i = 0; i < m; i++)
//			scanf_s("%d", &b[i]);
//	}
//
//	//MPI_Bcast(&m, 1, MPI_INT, root, MPI_COMM_WORLD);
//	if (rank == root)
//	{
//		for (int i = 0; i < size; i++)
//			if (i != root)
//				MPI_Send(&m, 1, MPI_INT, i, 0, MPI_COMM_WORLD);
//	}
//	else
//		MPI_Recv(&m, 1, MPI_INT, root, 0, MPI_COMM_WORLD, &status);
//
//	local_size = m / size;
//
//	local_a = (int*)malloc(sizeof(int) * local_size);
//	local_b = (int*)malloc(sizeof(int) * local_size);
//
//	// MPI_Scatter(a, local_size, MPI_INT, local_a, local_size, MPI_INT, root, MPI_COMM_WORLD);
//	if (rank == root)
//	{
//		for (int i = 0; i < size; i++)
//			if (i != root)
//				MPI_Send(a + i * local_size, local_size, MPI_INT, i, 0, MPI_COMM_WORLD);
//	}
//	else
//		MPI_Recv(local_a, local_size, MPI_INT, root, 0, MPI_COMM_WORLD, &status);
//
//	//MPI_Scatter(b, local_size, MPI_INT, local_b, local_size, MPI_INT, root, MPI_COMM_WORLD);
//	if (rank == root)
//	{
//		for (int i = 0; i < size; i++)
//			if (i != root)
//				MPI_Send(b + i * local_size, local_size, MPI_INT, i, 0, MPI_COMM_WORLD);
//
//		for (int i = 0; i < local_size; i++)
//		{
//			local_a[i + root] = a[i + root];
//			local_b[i + root] = b[i + root];
//		}
//	}
//	else
//		MPI_Recv(local_b, local_size, MPI_INT, root, 0, MPI_COMM_WORLD, &status);
//
//	local_skalar = 0;
//	for (int i = 0; i < local_size; i++)
//		local_skalar += local_a[i] * local_b[i];
//
//	//MPI_Reduce(&local_skalar, &skalar, 1, MPI_INT, MPI_SUM, root, MPI_COMM_WORLD);
//	if (rank == root)
//	{
//		skalar = local_skalar;
//		for (int i = 0; i < size; i++)
//		{
//			if (i != root)
//			{
//				MPI_Recv(&local_skalar, 1, MPI_INT, i, 0, MPI_COMM_WORLD, &status);
//				skalar += local_skalar;
//			}
//		}
//	}
//	else
//		MPI_Send(&local_skalar, 1, MPI_INT, root, 0, MPI_COMM_WORLD);
//
//	if (rank == root)
//	{
//		printf("Skalarni proizvod vektora iznosi: %d.\n", skalar);
//		fflush(stdout);
//	}
//
//	free(a);
//	free(b);
//	free(local_a);
//	free(local_b);
//
//	MPI_Finalize();
//
//	return 0;
//}