import { buildClerkProps } from 'svelte-clerk/server';
import type { LayoutServerLoad } from './$types';

export const load: LayoutServerLoad = async ({ locals, fetch }) => {
	const { getToken, userId } = locals.auth();

	// Not logged in: return empty data
	if (!userId) {
		return { apiKey: null, ...buildClerkProps(locals.auth()) };
	}

	const token = await getToken();
	const baseUrl = import.meta.env.VITE_API_BASE_URL;
	let apiKey = null;

	try {
		const res = await fetch(`${baseUrl}/api/key`, {
			headers: { Authorization: `Bearer ${token}` }
		});

		if (!res.ok) {
			console.error('Backend error:', res.status, res.statusText);
		} else {
			apiKey = await res.json();
		}
	} catch (err) {
		// Network-level error: backend offline, wrong host, etc.
		console.error('Fetch failed:', err);
	}

	return {
		apiKey,
		...buildClerkProps(locals.auth())
	};
};
