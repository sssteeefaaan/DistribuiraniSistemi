//#include <mpi.h>
//#include <stdio.h>
//#include <stdlib.h>
//
//int main(int argc, char** argv)
//{
//	int rank,
//		size,
//		n,
//		k,
//		* vector = nullptr,
//		* matrix = nullptr,
//		* c = nullptr,
//		* columnj = nullptr,
//		* rowProduct = nullptr,
//		root = 0,
//		bi;
//
//	MPI_Status status;
//	struct data {
//		int val;
//		int rank;
//	};
//
//	data in, out;
//
//	MPI_Init(&argc, &argv);
//	MPI_Comm_size(MPI_COMM_WORLD, &size);
//	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
//
//	if (rank == 0)
//	{
//		printf("Unesite n: ");
//		fflush(stdout);
//		scanf_s("%d", &n);
//
//		printf("Unesite k: ");
//		fflush(stdout);
//		scanf_s("%d", &k);
//	}
//
//	//MPI_Bcast(&n, 1, MPI_INT, root, MPI_COMM_WORLD);
//	if (rank == root)
//		for (int i = 0; i < size; i++)
//			if (i == root)
//				continue;
//			else
//				MPI_Send(&n, 1, MPI_INT, i, 0, MPI_COMM_WORLD);
//	else
//		MPI_Recv(&n, 1, MPI_INT, root, 0, MPI_COMM_WORLD, &status);
//
//	//MPI_Bcast(&k, 1, MPI_INT, root, MPI_COMM_WORLD);
//	if (rank == root)
//		for (int i = 0; i < size; i++)
//			if (i == root)
//				continue;
//			else
//				MPI_Send(&k, 1, MPI_INT, i, 0, MPI_COMM_WORLD);
//	else
//		MPI_Recv(&k, 1, MPI_INT, root, 0, MPI_COMM_WORLD, &status);
//
//	if (rank == 0)
//	{
//		vector = (int*)malloc(sizeof(int) * k);
//		printf("Unesite vektor: ");
//		fflush(stdout);
//		for (int i = 0; i < k; i++)
//			scanf_s("%d", &vector[i]);
//	}
//
//	MPI_Scatter(vector, 1, MPI_INT, &bi, 1, MPI_INT, root, MPI_COMM_WORLD);
//	if (rank == root)
//	{
//		for (int i = 0; i < size; i++)
//		{
//			if (i == root)
//			{
//				bi = vector[i];
//				continue;
//			}
//			else
//				MPI_Send(&vector[i], 1, MPI_INT, i, 0, MPI_COMM_WORLD);
//		}
//	}
//	else
//		MPI_Recv(&bi, 1, MPI_INT, root, 0, MPI_COMM_WORLD, &status);
//
//	if (rank == 0)
//	{
//		matrix = (int*)malloc(sizeof(int) * n * k);
//		printf("Unesite matricu\n");
//		for (int i = 0; i < n; i++)
//		{
//			printf("Unesite elemente %d. vrste matrice: ", i + 1);
//			fflush(stdout);
//			for (int j = 0; j < k; j++)
//				scanf_s("%d", &matrix[i * k + j]);
//		}
//	}
//
//	columnj = (int*)malloc(sizeof(int) * n);
//	if (rank == root)
//	{
//		for (int j = 0; j < k; j++)
//		{
//			if (j == root)
//				continue;
//			for (int i = 0; i < n; i++)
//				MPI_Send(&matrix[i * k + j], 1, MPI_INT, j, 0, MPI_COMM_WORLD);
//		}
//
//		for (int i = 0; i < n; i++)
//			columnj[i] = matrix[i * k + root];
//	}
//	else
//		for (int i = 0; i < n; i++)
//			MPI_Recv(&columnj[i], 1, MPI_INT, root, 0, MPI_COMM_WORLD, &status);
//
//	rowProduct = (int*)malloc(sizeof(int) * n);
//	//MPI_Reduce(columnj, rowProduct, n, MPI_INT, MPI_PROD, root, MPI_COMM_WORLD);
//	if (rank == root)
//	{
//		int* temp = (int*)malloc(sizeof(int) * n);
//		for (int j = 0; j < n; j++)
//			temp[j] = columnj[j];
//
//		for (int i = 0; i < k; i++)
//		{
//			if (i == root)
//				continue;
//			else
//			{
//				MPI_Recv(rowProduct, n, MPI_INT, i, 0, MPI_COMM_WORLD, &status);
//				for (int j = 0; j < n; j++)
//					temp[j] *= rowProduct[j];
//			}
//		}
//
//		for (int j = 0; j < n; j++)
//			rowProduct[j] = temp[j];
//	}
//	else
//		MPI_Send(columnj, n, MPI_INT, root, 0, MPI_COMM_WORLD);
//
//	if (rank == root)
//	{
//		printf("Proizvod elemenata svake vrste: [ ");
//		for (int i = 0; i < n; i++)
//			printf("%d ", rowProduct[i]);
//		printf("]\n");
//		fflush(stdout);
//	}
//
//	in.val = INT16_MAX;
//	in.rank = rank;
//	for (int i = 0; i < n; i++)
//	{
//		if (columnj[i] < in.val)
//			in.val = columnj[i];
//		columnj[i] *= bi;
//	}
//
//	//MPI_Reduce(&in, &out, 1, MPI_2INT, MPI_MINLOC, root, MPI_COMM_WORLD);
//	if (rank == root)
//	{
//		struct data temp = in;
//
//		for (int i = 0; i < k; i++)
//		{
//			if (i == root)
//				continue;
//			else
//			{
//				MPI_Recv(&out, 1, MPI_2INT, i, 0, MPI_COMM_WORLD, &status);
//				if (out.val < temp.val)
//					temp = out;
//			}
//		}
//
//		out = temp;
//	}
//	else
//		MPI_Send(&in, 1, MPI_2INT, root, 0, MPI_COMM_WORLD);
//
//	//MPI_Bcast(&out, 1, MPI_2INT, root, MPI_COMM_WORLD);
//	if (rank == root)
//		for (int i = 0; i < size; i++)
//			if (i == root)
//				continue;
//			else
//				MPI_Send(&out, 1, MPI_2INT, i, 0, MPI_COMM_WORLD);
//	else
//		MPI_Recv(&out, 1, MPI_2INT, root, 0, MPI_COMM_WORLD, &status);
//
//	c = (int*)malloc(sizeof(int) * n);
//
//	//MPI_Reduce(columnj, c, n, MPI_INT, MPI_SUM, out.rank, MPI_COMM_WORLD);
//	if (rank == root)
//	{
//		int* temp = (int*)malloc(sizeof(int) * n);
//		for (int j = 0; j < n; j++)
//			temp[j] = columnj[j];
//
//		for (int i = 0; i < k; i++)
//		{
//			if (i == root)
//				continue;
//			else
//			{
//				MPI_Recv(c, n, MPI_INT, i, 0, MPI_COMM_WORLD, &status);
//				for (int j = 0; j < n; j++)
//					temp[j] += c[j];
//			}
//		}
//
//		for (int j = 0; j < n; j++)
//			c[j] = temp[j];
//	}
//	else
//		MPI_Send(columnj, n, MPI_INT, root, 0, MPI_COMM_WORLD);
//
//	if (rank == out.rank)
//	{
//		printf("Proces [%d] sadrzi kolonu sa najmanjim elementom => %d.\n", out.rank, out.val);
//		printf("Proizvod matrice i vektora je: [ ");
//		for (int i = 0; i < n; i++)
//			printf("%d ", c[i]);
//		printf("]\n");
//		fflush(stdout);
//	}
//
//	free(vector);
//	free(matrix);
//	free(rowProduct);
//	free(c);
//	free(columnj);
//
//	MPI_Finalize();
//	return 0;
//}