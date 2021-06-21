//#include <mpi.h>
//#include <stdio.h>
//#include <stdlib.h>
//
//bool isPrime(int number);
//int main(int argc, char** argv)
//{
//	int rank, size, parc, n, rez, sabirci;
//	struct {
//		int val;
//		int rank;
//	}in, out;
//
//	MPI_Init(&argc, &argv);
//	MPI_Comm_size(MPI_COMM_WORLD, &size);
//	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
//
//	if (rank == 0)
//	{
//		printf("Unesite n:\n");
//		fflush(stdout);
//		scanf_s("%d", &n);
//	}
//
//	MPI_Bcast(&n, 1, MPI_INT, 0, MPI_COMM_WORLD);
//
//	parc = in.val= 0;
//	in.rank = rank;
//	sabirci = 0;
//
//	for (int j = 0; j < n; j++)
//	{
//		for (int i = rank; i < n; i += size)
//		{
//			if (isPrime(i+j))
//				in.val++;
//			parc += i + j;
//			sabirci++;
//		}
//	}
//
//	printf("Proces [%d] je imao %d sabiraka.\n", rank, sabirci);
//	fflush(stdout);
//
//	MPI_Reduce(&in, &out, 1, MPI_2INT, MPI_MINLOC, 0, MPI_COMM_WORLD);
//	MPI_Bcast(&out, 1, MPI_2INT, 0, MPI_COMM_WORLD);
//	MPI_Reduce(&parc, &rez, 1, MPI_INT, MPI_SUM, out.rank, MPI_COMM_WORLD);
//
//	if (rank == out.rank)
//	{
//		printf("Proces [%d] je imao najmanje prostih sabiraka => %d.\n", out.rank, out.val);
//		printf("Vrednost sume iznosi: %d.\n", rez);
//
//		rez = 0;
//		for (int i = 0; i < n; i++)
//			for (int j = 0; j < n; j++)
//				rez += i + j;
//		printf("Suma iznosi: %d.\n", rez);
//	}
//
//	MPI_Finalize();
//	return 0;
//}
//
//bool isPrime(int number)
//{
//	if (number == 2 || number == 3)
//		return true;
//
//	if (number <= 1 || number % 2 == 0 || number % 3 == 0)
//		return false;
//
//	for (int i = 5; i * i <= number; i += 6)
//		if (number % i == 0 || number % (i + 2) == 0)
//			return true;
//
//	return false;
//}