import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  server:{
    proxy: {
      '/api': {
	target:'http://http://10.4.56.95/:8080',
        changeOrigin: true,
        secure: false,
        // rewrite: (path) => {
        //   console.log("path");
        //   return path.replace(/^\/api/,'')}
      }
    }
  }
})
