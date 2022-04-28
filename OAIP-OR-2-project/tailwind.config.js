module.exports = {
  content: ["./index.html", "./src/**/*.{vue,js,ts,jsx,tsx}"],
  theme: {
    extend: {
      colors: {
        "toy-blue": "#5867EB",
        "toy-red": "#EB5858",
        "toy-green":"#7CAA94",
        "toy-yellow":"#F6DA75",
        "pastel-green":"#CFE8DC",
        "dark-green":"#5F776C",
        "darker-green": "#495E54",
        "dark-orange": "#C2735A"
      },
    },
    fontFamily: {
      'Mitr': ['Mitr', 'sans-serif'],
      'Kanit': ['Kanit', 'sans-serif']
    },
    plugins: [],
  },
};