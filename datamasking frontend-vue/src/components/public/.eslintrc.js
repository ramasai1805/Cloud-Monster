module.exports = {
  parserOptions: {
    parser: 'babel-eslint',
    ecmaVersion: 6
  },
  env: {
    node: true,
  },
  extends: [
    'eslint:recommended',
    'plugin:vue/vue3-recommended',
  ],
  rules: {
    // override/add rules settings here, such as:
    // 'vue/no-unused-vars': 'error'
    // "vue/no-parsing-error": 'error'
  }
}