/** @type {import('tailwindcss').Config} */
export default {
  content: ['./src/**/*.{html,js,svelte,ts}'],
  theme: {
    extend: {
      colors: {
        'error-red': 'oklch(var(--error-red) / <alpha-value>)',
      },
    },
  },
  daisyui: {
    themes: [
      {
        sunset: {
          ...require('daisyui/src/theming/themes')['sunset'],
          '--error-red': '53.24% 0.2294 26.47',
          '.input-error-red': {
            '--tw-border-opacity': '1',
            'border-color':
              'var(--fallback-p,oklch(var(--error-red)/var(--tw-border-opacity)))',
          },
          '.btn-error-red': {
            '--btn-color': 'var(--error-red)',
            color: 'white',
          },
        },
      },
    ],
  },
  plugins: [require('daisyui')],
}
