const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave:false,

  pluginOptions: {
    vuetify: {
			// https://github.com/vuetifyjs/vuetify-loader/tree/next/packages/vuetify-loader
		}
  },
  // 개발 서버 설정
    devServer: {
        // 프록시 설정
        proxy: {
            // 프록시 요청을 보낼 api의 시작 부분
            '/ROOT': {
                // 프록시 요청을 보낼 서버의 주소
                target: 'http://localhost:9090',
                changeOrigin: true,
                logLevel: 'debug',
            }
        },
    },
    

    // 리소스의 위치
    // publicPath : '/ROOT/vue/'
})
  
