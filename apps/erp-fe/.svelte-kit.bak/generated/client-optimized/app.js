export { matchers } from './matchers.js';

export const nodes = [
	() => import('./nodes/0'),
	() => import('./nodes/1'),
	() => import('./nodes/2'),
	() => import('./nodes/3'),
	() => import('./nodes/4'),
	() => import('./nodes/5'),
	() => import('./nodes/6'),
	() => import('./nodes/7'),
	() => import('./nodes/8'),
	() => import('./nodes/9'),
	() => import('./nodes/10'),
	() => import('./nodes/11')
];

export const server_loads = [0];

export const dictionary = {
		"/": [2],
		"/finances/currencies": [3],
		"/finances/money-operation": [4],
		"/finances/money-operation/periodical": [5],
		"/finances/object-value": [~6],
		"/finances/operation-category": [7],
		"/finances/planned-expenses": [8],
		"/inventory": [9],
		"/inventory/categories": [10],
		"/inventory/items": [11]
	};

export const hooks = {
	handleError: (({ error }) => { console.error(error) }),
};

export { default as root } from '../root.svelte';