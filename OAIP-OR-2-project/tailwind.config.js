module.exports = {
  content: ["./index.html", "./src/**/*.{vue,js,ts,jsx,tsx}"],
  theme: {
    extend: {
      colors: {
        "dark-orange": "#C2735A",
        "pastel-orange": "#FDD3B8",
        "blood-bird": "#886C6C",
        "pastel-pink": "#FFC2C2",
        "pastel-yellow": "#F6E7B2",
        "dark-pink": "#D58EB6",
        "dark-green": "#5F776C",
        "cute-orange": "#FBBF98"
       
        
      },
    },
    fontFamily: {
      Mitr: ["Mitr", "sans-serif"],
      Kanit: ["Kanit", "sans-serif"],
    },
    backgroundImage: {
      wave: "url('/src/assets/background.jpg')",
    },
    plugins: [],
  },
};