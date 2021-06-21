#include <stdio.h>
#include <mpi.h>

int main(int argc, char** argv)
{
	int rank, size, div, n;
	float* a = nullptr, * b = nullptr, * local_a, * local_b;
	double scal, local_scal;
	
	MPI_Init(&argc, &argv);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
	MPI_Comm_size(MPI_COMM_WORLD, &size);

	if (rank == 0)
		scanf_s("%d", &n);

	MPI_Bcast(&n, 1, MPI_INT, 0, MPI_COMM_WORLD);

	div = n / size;

	local_a = new float[div];
	local_b = new float[div];

	if (rank == 0)
	{
		a = new float[n];
		for (int i = 0; i < n; i++)
			scanf_s("%f", &a[i]);
	}

	MPI_Scatter(a, div, MPI_FLOAT, local_a, div, MPI_FLOAT, 0, MPI_COMM_WORLD);

	if (rank == 0)
	{
		b = new float[n];
		for (int i = 0; i < n; i++)
			scanf_s("%f", &b[i]);
	}

	MPI_Scatter(b, div, MPI_FLOAT, local_b, div, MPI_FLOAT, 0, MPI_COMM_WORLD);

	local_scal = 0;
	for (int i = 0; i < div; i++)
	{
		local_scal +=(double) local_a[i] * local_b[i];
	}

	MPI_Reduce(&local_scal, &scal, 1, MPI_DOUBLE, MPI_SUM, 0, MPI_COMM_WORLD);

	if (rank == 0)
	{
		printf("Vektor a = [");
		for (int i = 0; i < n; i++) printf("%f ", a[i]);

		printf("]\nVektor b = [");
		for (int i = 0; i < n; i++) printf("%f ", b[i]);

		printf("]\nSkalarni proizvod vektora iznosi: %f\n", scal);
	}

	MPI_Finalize();

	return 0;
}