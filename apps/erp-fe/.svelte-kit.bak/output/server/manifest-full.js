export const manifest = {
	appDir: "_app",
	appPath: "_app",
	assets: new Set(["favicon.png"]),
	mimeTypes: {".png":"image/png"},
	_: {
		client: {"start":"_app/immutable/entry/start.e37b1220.js","app":"_app/immutable/entry/app.c4ef8215.js","imports":["_app/immutable/entry/start.e37b1220.js","_app/immutable/chunks/scheduler.f526bf47.js","_app/immutable/chunks/singletons.4e1313c7.js","_app/immutable/chunks/index.0e775aa0.js","_app/immutable/entry/app.c4ef8215.js","_app/immutable/chunks/scheduler.f526bf47.js","_app/immutable/chunks/index.69537f49.js"],"stylesheets":[],"fonts":[]},
		nodes: [
			() => import('./nodes/0.js'),
			() => import('./nodes/1.js'),
			() => import('./nodes/2.js'),
			() => import('./nodes/3.js'),
			() => import('./nodes/4.js'),
			() => import('./nodes/5.js'),
			() => import('./nodes/6.js'),
			() => import('./nodes/7.js'),
			() => import('./nodes/8.js'),
			() => import('./nodes/9.js'),
			() => import('./nodes/10.js'),
			() => import('./nodes/11.js')
		],
		routes: [
			{
				id: "/",
				pattern: /^\/$/,
				params: [],
				page: { layouts: [0,], errors: [1,], leaf: 2 },
				endpoint: null
			},
			{
				id: "/api/finances/currencies",
				pattern: /^\/api\/finances\/currencies\/?$/,
				params: [],
				page: null,
				endpoint: () => import('./entries/endpoints/api/finances/currencies/_server.ts.js')
			},
			{
				id: "/api/finances/money-operation",
				pattern: /^\/api\/finances\/money-operation\/?$/,
				params: [],
				page: null,
				endpoint: () => import('./entries/endpoints/api/finances/money-operation/_server.ts.js')
			},
			{
				id: "/api/finances/money-operation/periodical",
				pattern: /^\/api\/finances\/money-operation\/periodical\/?$/,
				params: [],
				page: null,
				endpoint: () => import('./entries/endpoints/api/finances/money-operation/periodical/_server.ts.js')
			},
			{
				id: "/api/finances/object-value",
				pattern: /^\/api\/finances\/object-value\/?$/,
				params: [],
				page: null,
				endpoint: () => import('./entries/endpoints/api/finances/object-value/_server.ts.js')
			},
			{
				id: "/api/finances/operation-category",
				pattern: /^\/api\/finances\/operation-category\/?$/,
				params: [],
				page: null,
				endpoint: () => import('./entries/endpoints/api/finances/operation-category/_server.ts.js')
			},
			{
				id: "/api/finances/planned-expenses",
				pattern: /^\/api\/finances\/planned-expenses\/?$/,
				params: [],
				page: null,
				endpoint: () => import('./entries/endpoints/api/finances/planned-expenses/_server.ts.js')
			},
			{
				id: "/api/inventory/categories",
				pattern: /^\/api\/inventory\/categories\/?$/,
				params: [],
				page: null,
				endpoint: () => import('./entries/endpoints/api/inventory/categories/_server.ts.js')
			},
			{
				id: "/api/inventory/items",
				pattern: /^\/api\/inventory\/items\/?$/,
				params: [],
				page: null,
				endpoint: () => import('./entries/endpoints/api/inventory/items/_server.ts.js')
			},
			{
				id: "/api/inventory/items/object-names",
				pattern: /^\/api\/inventory\/items\/object-names\/?$/,
				params: [],
				page: null,
				endpoint: () => import('./entries/endpoints/api/inventory/items/object-names/_server.ts.js')
			},
			{
				id: "/finances/currencies",
				pattern: /^\/finances\/currencies\/?$/,
				params: [],
				page: { layouts: [0,], errors: [1,], leaf: 3 },
				endpoint: null
			},
			{
				id: "/finances/money-operation",
				pattern: /^\/finances\/money-operation\/?$/,
				params: [],
				page: { layouts: [0,], errors: [1,], leaf: 4 },
				endpoint: null
			},
			{
				id: "/finances/money-operation/periodical",
				pattern: /^\/finances\/money-operation\/periodical\/?$/,
				params: [],
				page: { layouts: [0,], errors: [1,], leaf: 5 },
				endpoint: null
			},
			{
				id: "/finances/object-value",
				pattern: /^\/finances\/object-value\/?$/,
				params: [],
				page: { layouts: [0,], errors: [1,], leaf: 6 },
				endpoint: null
			},
			{
				id: "/finances/operation-category",
				pattern: /^\/finances\/operation-category\/?$/,
				params: [],
				page: { layouts: [0,], errors: [1,], leaf: 7 },
				endpoint: null
			},
			{
				id: "/finances/planned-expenses",
				pattern: /^\/finances\/planned-expenses\/?$/,
				params: [],
				page: { layouts: [0,], errors: [1,], leaf: 8 },
				endpoint: null
			},
			{
				id: "/inventory",
				pattern: /^\/inventory\/?$/,
				params: [],
				page: { layouts: [0,], errors: [1,], leaf: 9 },
				endpoint: null
			},
			{
				id: "/inventory/categories",
				pattern: /^\/inventory\/categories\/?$/,
				params: [],
				page: { layouts: [0,], errors: [1,], leaf: 10 },
				endpoint: null
			},
			{
				id: "/inventory/items",
				pattern: /^\/inventory\/items\/?$/,
				params: [],
				page: { layouts: [0,], errors: [1,], leaf: 11 },
				endpoint: null
			}
		],
		matchers: async () => {
			
			return {  };
		}
	}
};
