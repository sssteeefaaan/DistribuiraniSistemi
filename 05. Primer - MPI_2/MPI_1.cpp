#include <stdio.h>
#include <mpi.h>
#include <random>
#include <time.h>

/* Napisati MPI program koji nalazi minimalnu i maximalnu vrednost zadate promenljive
za N procesa kao i identifikatore procesa koji sadrže te vrednosti*/

int main(int argc, char** argv)
{
	srand(time(NULL));

	int my_rank, size;
	struct {
		int vrednost;
		int id;
	} in, out;

	MPI_Init(&argc, &argv);
	MPI_Comm_rank(MPI_COMM_WORLD, &my_rank);
	MPI_Comm_size(MPI_COMM_WORLD, &size);

	in.vrednost = (rand() % 255 + 1) * my_rank;
	in.id = my_rank;

	MPI_Reduce(&in, &out, 1, MPI_2INT, MPI_MAXLOC, 0, MPI_COMM_WORLD);

	if(my_rank == 0)
		printf("Najveca vrednost iznosi: %d, a proces koji je ima je sa id-om: %d\n", out.vrednost, out.id);

	MPI_Finalize();

	return 0;
}