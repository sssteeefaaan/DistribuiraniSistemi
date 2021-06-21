//#include <mpi.h>
//#include <stdio.h>
//
//int main(int argc, char** argv)
//{
//	int rank,
//		size,
//		n,
//		partial,
//		rez,
//		root = 0,
//		* prvi = nullptr,
//		* drugi = nullptr,
//		perSegment,
//		* prviPart = nullptr,
//		* drugiPart = nullptr;
//
//	MPI_Init(&argc, &argv);
//	MPI_Comm_size(MPI_COMM_WORLD, &size);
//	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
//
//	if (rank == root)
//	{
//		printf("Unesite dimenziju vektora: ");
//		fflush(stdout);
//		scanf_s("%d", &n);
//	}
//
//	MPI_Bcast(&n, 1, MPI_INT, root, MPI_COMM_WORLD);
//
//	perSegment = n / size;
//	prviPart = new int[perSegment];
//	drugiPart = new int[perSegment];
//
//	if (rank == root)
//	{
//		prvi = new int[n];
//		printf("Unesite elemente prvog vektora: ");
//		fflush(stdout);
//		for (int i = 0; i < n; i++)
//			scanf_s("%d", &prvi[i]);
//	}
//
//	MPI_Scatter(prvi, perSegment, MPI_INT, prviPart, perSegment, MPI_INT, root, MPI_COMM_WORLD);
//
//	if (rank == root)
//	{
//		drugi = new int[n];
//		printf("Unesite elemente drugog vektora: ");
//		fflush(stdout);
//		for (int i = 0; i < n; i++)
//			scanf_s("%d", &drugi[i]);
//	}
//
//	MPI_Scatter(drugi, perSegment, MPI_INT, drugiPart, perSegment, MPI_INT, root, MPI_COMM_WORLD);
//
//	partial = 0;
//	for (int i = 0; i < perSegment; i++)
//		partial += prviPart[i] * drugiPart[i];
//
//	MPI_Reduce(&partial, &rez, 1, MPI_INT, MPI_SUM, root, MPI_COMM_WORLD);
//
//	if (rank == root)
//	{
//		printf("Skalarni proizvod vektora iznosi: %d\n", rez);
//		fflush(stdout);
//	}
//
//	if (prvi != nullptr) {
//		delete[] prvi;
//		prvi = nullptr;
//	}
//
//	if (drugi != nullptr) {
//		delete[] drugi;
//		drugi = nullptr;
//	}
//
//	if (prviPart != nullptr) {
//		delete[] prviPart;
//		prviPart = nullptr;
//	}
//
//	if (drugiPart != nullptr) {
//		delete[] drugiPart;
//		drugiPart = nullptr;
//	}
//
//	MPI_Finalize();
//
//	return 0;
//}