const { defineConfig } = require("@vue/cli-service");
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    proxy: {
      "^/api/admin": {
        target: "http://10.20.5.34:9999",
        changeOrigin: true,
        pathRewrite: { "^/api": "" },
      },
      "^/api/support": {
        target: "http://10.20.5.34:9999",
        changeOrigin: true,
        pathRewrite: { "^/api": "" },
      },
    },
  },
});
