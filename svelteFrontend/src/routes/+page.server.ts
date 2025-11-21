import type { Actions } from './$types';
import { fail } from '@sveltejs/kit';

export const actions = {
	update: async ({ locals, request }) => {
		console.log('UPDATING');
		// 1. Authentication
		const { getToken, userId } = locals.auth();

		if (!userId) {
			return fail(401, { message: 'Unauthorized' });
		}

		const token = await getToken({ template: 'GitProjectsAPIBackend' });

		// 2. Extract the data sent from the client
		const formData = await request.formData();
		const optionsJson = formData.get('optionsPayload');

		if (!optionsJson || typeof optionsJson !== 'string') {
			return fail(400, { message: 'Invalid data' });
		}

		const options = JSON.parse(optionsJson);

		// 3. Call the External API
		const baseUrl = import.meta.env.VITE_API_BASE_URL;

		try {
			const response = await fetch(`${baseUrl}/api/options`, {
				method: 'PUT',
				body: JSON.stringify(options), // Sends: { aiModel, blacklist, whitelist }
				headers: {
					'Content-Type': 'application/json',
					Authorization: `Bearer ${token}` // Token is now secure!
				}
			});

			if (!response.ok) {
				return fail(response.status, { message: 'Failed to update backend' });
			}

			return { success: true };
		} catch (error) {
			console.error(error);
			return fail(500, { message: 'Network error' });
		}
	}
} satisfies Actions;
