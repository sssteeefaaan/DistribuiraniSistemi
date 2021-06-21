//Napisati MPI program koji izraCunava vrednost broja PI kao
// vrednost integrala funckije f(x) = 4/(1+x^2) na intervalu [0,1]

#include <stdio.h>
#include <mpi.h>
#include <math.h>

int main(int argc, char** argv)
{
	int rank, size;

	MPI_Init(&argc, &argv);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
	MPI_Comm_size(MPI_COMM_WORLD, &size);

	int N;
	double h, x, part_sum = 0, sum;

	if (!rank) {
		printf("Unesite broj segmenata dx: ");
		scanf_s("%d", &N);
	}

	MPI_Bcast(&N, 1, MPI_INT, 0, MPI_COMM_WORLD);

	h = 1 / (double)N;
	for (int i = rank; i < N; i += size) {
		x = (i + 0.5) * h;
		part_sum += (double)4 / (1 + x * x);
	}

	MPI_Reduce(&part_sum, &sum, 1, MPI_DOUBLE, MPI_SUM, 0, MPI_COMM_WORLD);

	sum *= h;
	if (!rank)
		printf("Vrednost integrala iznosi: %f\n", sum);

	MPI_Finalize();

	return 0;
}