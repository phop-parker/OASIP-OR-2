import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  server:{
    proxy: {
      '/api': {
        target:'http://localhost:8080',
        changeOrigin: true,
        secure: false,
        // rewrite: (path) => {
        //   console.log("path");
        //   return path.replace(/^\/api/,'')}
      }
    }
  }
})
