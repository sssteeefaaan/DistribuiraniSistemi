#include <stdio.h>
#include <mpi.h>
#include <math.h>
#define N 30
#define val (double)3.14

int main(int argc, char** argv)
{
	int my_rank, size;

	MPI_Init(&argc, &argv);
	MPI_Comm_rank(MPI_COMM_WORLD, &my_rank);
	MPI_Comm_size(MPI_COMM_WORLD, &size);

	struct {
		double vrednost;
		int rank;
	} in[N], out[N];

	for (int i = 0; i < N; i++) {
		in[i].vrednost = (double)(my_rank + 1) * val - i*2;
		in[i].rank = my_rank;
	}

	MPI_Reduce(&in, &out, N, MPI_DOUBLE_INT, MPI_MAXLOC, 0, MPI_COMM_WORLD);

	if (!my_rank)
		for (int i = 0; i < N; i++)
			printf("Proces koji ima %d. najvecu vrednost [%d]: %f\n", i + 1, out[i].rank, out[i].vrednost);
	
	MPI_Finalize();

	return 0;
}