import axios from "axios";

export default {
  state: {
    loginState: [],
    loginbool: localStorage.getItem("id") ? true : false,
    loginUser: localStorage.getItem("id") ? localStorage.getItem("id") : "",
  },
  getters: {
    getLoginState: (state) => state.loginState,
    getLoginBool: (state) => state.loginbool,
    getLoginUser: (state) => state.loginUser,
  },
  mutations: {
    setLoginState: (state, value) => (state.loginState = value),
    setLoginBool: (state, value) => (state.loginbool = value),
    setLoginUser: (state, value) => (state.loginUser = value),
    resetLogin: (state) => {
      state.loginbool = false;
      state.loginState = [];
      localStorage.clear();
    },
  },
  actions: {
    async LOGIN_STATE({ commit }, { details, success }) {
      //   debugger;
      const res = await axios.post("/api/admin/login", details);
      console.log(res);
      localStorage.setItem("id", res.data);
      //   localStorage.setItem("status", res.status);
      success(res);
      commit("setLoginState", res);
      commit("setLoginBool", true);
    },
    async RESET_LOGIN({ commit }, { eid }) {
      const res = await axios.post(`/api/support/logout/${eid}`);
      commit("resetLogin");
      console.log(res);
    },
  },
};
