import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
export default defineConfig({
  plugins: [vue()],
  base:`/or2/`,
  server:{
    proxy: {
      '/api': {
	      target:'http://http://10.4.56.95:8080',
        changeOrigin: true,
        secure: false,
      }
    }
  }
})
