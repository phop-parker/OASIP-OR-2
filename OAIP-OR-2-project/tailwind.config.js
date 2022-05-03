module.exports = {
  content: ["./index.html", "./src/**/*.{vue,js,ts,jsx,tsx}"],
  theme: {
    extend: {
      colors: {
        "pastel-green":"#CFE8DC",
        "dark-green":"#5F776C",
        "darker-green": "#495E54",
        "dark-orange": "#C2735A",
        "pastel-orange": "#FDD3B8",
        "blood-bird":"#886C6C"
      },
    },
    fontFamily: {
      'Mitr': ['Mitr', 'sans-serif'],
      'Kanit': ['Kanit', 'sans-serif']
    },
    backgroundImage: {
      'wave': "url('/src/assets/bg-wave.png')"
    },
    plugins: [],
  },
};