//#include <stdio.h>
//#include <stdlib.h>
//#include <mpi.h>
//
//int main(int argc, char** argv)
//{
//	int rank, size, a, b, x, root = 0;
//
//	struct {
//		int val;
//		int rank;
//	}minIN, minOUT, prikazIN, prikazOUT;
//
//	MPI_Init(&argc, &argv);
//	MPI_Comm_size(MPI_COMM_WORLD, &size);
//	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
//
//	if (rank == root)
//	{
//		printf("Unesite donju granicu intervala: ");
//		fflush(stdout);
//		scanf_s("%d", &a);
//
//		printf("Unesite gornju granicu intervala: ");
//		fflush(stdout);
//		scanf_s("%d", &b);
//
//		printf("Unesite vrednost x: ");
//		fflush(stdout);
//		scanf_s("%d", &x);
//	}
//
//	MPI_Bcast(&a, 1, MPI_INT, root, MPI_COMM_WORLD);
//	MPI_Bcast(&b, 1, MPI_INT, root, MPI_COMM_WORLD);
//	MPI_Bcast(&x, 1, MPI_INT, root, MPI_COMM_WORLD);
//
//	minIN.val = b + 1;
//	prikazIN.val = 0;
//	minIN.rank = prikazIN.rank = rank;
//
//	for (int i = 2 * rank + a; i <= b; i += 2 * size)
//	{
//		printf("Proccess [%d]: %d\n", rank, i);
//		fflush(stdout);
//
//		if (i % x == 0)
//		{
//			prikazIN.val++;
//			if(minIN.val > i)
//				minIN.val = i;
//		}
//	}
//
//	MPI_Reduce(&prikazIN, &prikazOUT, 1, MPI_2INT, MPI_MINLOC, root, MPI_COMM_WORLD);
//	MPI_Bcast(&prikazOUT, 1, MPI_2INT, root, MPI_COMM_WORLD);
//	MPI_Reduce(&minIN, &minOUT, 1, MPI_2INT, MPI_MINLOC, prikazOUT.rank, MPI_COMM_WORLD);
//
//	if (rank == prikazOUT.rank)
//	{
//		printf("Najmanji neparan broj u intervalu [%d, %d] koji je deljiv brojem %d je: %d.\n", a, b, x, minOUT.val);
//		printf("Generisao ga je proces [%d].\n", minOUT.rank);
//		printf("Ovu poruku prikazuje proccess [%d], koji ima najmanji broj sa navedenim osobinama => %d.\n", prikazOUT.rank, prikazOUT.val);
//		fflush(stdout);
//	}
//
//	MPI_Finalize();
//
//	return 0;
//}