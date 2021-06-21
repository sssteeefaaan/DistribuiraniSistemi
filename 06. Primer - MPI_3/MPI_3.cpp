#include <stdio.h>
#include <mpi.h>

struct twoInt{
	int first;
	int second;
};

int main(int argc, char** argv)
{
	int rank, size;

	MPI_Init(&argc, &argv);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
	MPI_Comm_size(MPI_COMM_WORLD, &size);

	struct twoInt granice, podaci, najmanjeResult, finalRez;
	int x, min, broj_brojeva;
	int* brojevi = nullptr;

	if (!rank)
		scanf_s("%d %d", &granice.first, &granice.second);

	MPI_Bcast(&granice, 1, MPI_2INT, 0, MPI_COMM_WORLD);

	if (!rank)
		scanf_s("%d", &x);

	MPI_Bcast(&x, 1, MPI_INT, 0, MPI_COMM_WORLD);

	broj_brojeva = 0;
	min = INT16_MAX;
	brojevi = new int[(granice.second - granice.first) / (2 * size)];
	for (int i = granice.first + 2 * rank; i <= granice.second; i += 2 * size) {
		if (!(i % x)) {
			brojevi[broj_brojeva++] = i;
			if (i < min)
				min = i;
		}
	}

	podaci.first = broj_brojeva;
	podaci.second = rank;

	MPI_Reduce(&podaci, &najmanjeResult, 1, MPI_2INT, MPI_MINLOC, 0, MPI_COMM_WORLD);

	MPI_Bcast(&najmanjeResult.second, 1, MPI_INT, 0, MPI_COMM_WORLD);

	podaci.first = min;
	podaci.second = rank;

	MPI_Reduce(&podaci, &finalRez, 1, MPI_2INT, MPI_MINLOC, najmanjeResult.second, MPI_COMM_WORLD);

	if (rank == najmanjeResult.second)
	{
		printf("Proces koji ima najmanje neparnih brojeva u opsegu [%d, %d] deljivih brojem %d je sa identifikatorom: %d\n", granice.first, granice.second, x, rank);
		printf("Najmanji broj koji zadovoljava osobine je: %d i generisao ga je proces sa identifikatorom %d.\n", finalRez.first, finalRez.second);
	}

	if (brojevi != nullptr)
	{
		delete[] brojevi;
		brojevi = nullptr;
	}

	MPI_Finalize();
	return 0;
}