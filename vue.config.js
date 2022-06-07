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
            '/AbilityMarket': {
                // 프록시 요청을 보낼 서버의 주소
                target: 'http://localhost:9090',
                changeOrigin: true,
                logLevel: 'debug',
            }
        },

        // sse 오류로 인한 추가 설정
        compress: false

    },
    
    // 리소스의 위치
    publicPath : '/AbilityMarket/vue/'
})
  
