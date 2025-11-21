export type ApiKey = {
	id: string; // UUID → string
	key: string; // UUID → string
	quota: number; // int → number
	issuedAt: string; // Instant → ISO timestamp string
	user?: User; // Optional because you might not always include it in responses
	timestamps: Array<Timestamp>;
	blacklist: Array<string>;
	whitelist: Array<string>;
	aiModel: string;
};

export type User = {
	id: string;
	name: string;
	surname: string;
	username: string;
	email: string;
	profileImage?: string;
};

export type Timestamp = {
	id: number;
	action: string;
	timestamp: Date; // ISO string
};

export type Options = {
	apiKey: string;
	blacklist: Array<string>;
	whitelist: Array<string>;
	aiModel: string;
};
