#include <stdio.h>
#include <mpi.h>
#include <string>

int main(int argc, char** argv)
{
	int rank, size;
	std::string poruke[2] = { "Prva", "Druga" };
	MPI_Status status;

	MPI_Init(&argc, &argv);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
	MPI_Comm_size(MPI_COMM_WORLD, &size);

	if (!rank)
	{
		MPI_Request request1, request2;
		for (int i = 1; i < size; i++)
		{
			MPI_Isend(poruke[0].c_str(), poruke[0].size(), MPI_CHAR, i, 1, MPI_COMM_WORLD, &request1);
			MPI_Isend(poruke[1].c_str(), poruke[1].size(), MPI_CHAR, i, 2, MPI_COMM_WORLD, &request2);

			MPI_Wait(&request1, &status);
			MPI_Wait(&request2, &status);
		}
	}
	else {
		int count;
		MPI_Probe(0, 2, MPI_COMM_WORLD, &status);
		MPI_Get_count(&status, MPI_CHAR, &count);

		char* rcvBuff = new char[count + 1];

		MPI_Recv(rcvBuff, count, MPI_CHAR, 0, 2, MPI_COMM_WORLD, &status);
		rcvBuff[count] = '\0';
		printf("[%d] Primio sam poruku: '%s'\n", rank, rcvBuff);

		if (rcvBuff) delete[]rcvBuff;

		MPI_Probe(0, 1, MPI_COMM_WORLD, &status);
		MPI_Get_count(&status, MPI_CHAR, &count);

		rcvBuff = new char[count + 1];

		MPI_Recv(rcvBuff, count, MPI_CHAR, 0, 1, MPI_COMM_WORLD, &status);
		rcvBuff[count] = '\0';
		printf("[%d] Primio sam poruku: '%s'\n", rank, rcvBuff);

		if (rcvBuff) delete[]rcvBuff;

		MPI_Send(&rank, 1, MPI_INT, 0, 0, MPI_COMM_WORLD);
	}

	if (!rank)
	{
		int val;
		for (int i = 1; i < size; i++)
		{
			MPI_Recv(&val, 1, MPI_INT, i, 0, MPI_COMM_WORLD, &status);
			printf("[%d] Proces {%d} je zavrsio!\n", rank, val);
		}
	}

	MPI_Finalize();
	return 0;
}