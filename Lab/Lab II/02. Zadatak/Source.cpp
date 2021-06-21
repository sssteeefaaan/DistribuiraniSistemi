#include <stdio.h>
#include <mpi.h>

int main(int argc, char** argv)
{
	int rank, size;

	MPI_Init(&argc, &argv);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
	MPI_Comm_size(MPI_COMM_WORLD, &size);

	int n;
	if (!rank)
		scanf_s("%d", &n);

	MPI_Bcast(&n, 1, MPI_INT, 0, MPI_COMM_WORLD);

	int each = n / size;
	float *vrednosti = new float[n];
	float* rcvBuff = new float[each];

	if(!rank)
		for (int i = 0; i < n; i++)
			vrednosti[i] = -2 * (i + 1);

	MPI_Scatter(&vrednosti[0], each, MPI_FLOAT, &rcvBuff[0], each, MPI_FLOAT, 0, MPI_COMM_WORLD);

	float minSend = INT16_MAX;
	for (int i = 0; i < each; i++) {
		if (minSend > rcvBuff[i])
			minSend = rcvBuff[i];
	}

	if (vrednosti)delete[] vrednosti;
	if (rcvBuff)delete[] rcvBuff;

	float minRez;
	MPI_Reduce(&minSend, &minRez, 1, MPI_FLOAT, MPI_MIN, 0, MPI_COMM_WORLD);

	if (!rank)
	{
		printf("Najmanji element niza je %f\n", minRez);
	}

	MPI_Finalize();
	return 0;
}