export type ApiKey = {
	id: string; // UUID → string
	key: string; // UUID → string
	quota: number; // int → number
	issuedAt: string; // Instant → ISO timestamp string
	user?: User; // Optional because you might not always include it in responses
};

type User = {
	id: string;
	name: string;
	surname: string;
	username: string;
	email: string;
	profileImage?: string;
};
