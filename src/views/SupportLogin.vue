<template>
  <div class="container">
    <!-- <nav-bar-support></nav-bar-support> -->
    <div class="login">
      <h2>Login</h2>
      <label>Email</label><br />
      <input
        v-model="email"
        type="text"
        placeholder="Enter Email"
        name="email"
        id="email"
        required
      />
      <br />
      <label>Password</label><br />
      <input
        v-model="password"
        type="password"
        placeholder="Enter Password"
        name="password"
        id="password"
        required
      />
      <br />
      <!-- <input
          v-model="type"
          type="text"
          placeholder="Enter Type"
          name="type"
          id="type"
          required
        /> -->
      <!-- <label for="Login">Type of Login:</label><br />
      <select v-model="logType" name="Login" id="Login" @change="typeChange">
        <option value="Admin">Admin</option>
        <option value="Support">SupportEngineer</option>
      </select>
      <br /> -->
      <button @click="LogType" class="button-19">Login</button>
    </div>
  </div>
</template>
<script>
import { mapActions, mapGetters } from "vuex";
import Vue from "vue";
import Toasted from "vue-toasted";
// import Toast from "vue-toast-notification";
// import "vue-toast-notification/dist/theme-default.css";

Vue.use(Toasted);

export default {
  components: {},
  data() {
    return {
      logType: "",
      type: "",
      email: "",
      password: "",
      boolValue: "",
      loginbool: false,
      list: [],
    };
  },
  computed: {
    ...mapGetters(["getLoginState"]),
  },
  methods: {
    ...mapActions(["LOGIN_STATE"]),
    typeChange(data) {
      data = this.logType;
      console.log(data);
      if (this.logType === "Admin") {
        this.boolValue = true;
      } else {
        this.boolValue = false;
      }
      console.log(this.boolValue);
    },
    LogType() {
      // debugger;
      const payload = {
        emailId: this.email,
        password: this.password,
      };
      this.$store
        .dispatch("LOGIN_STATE", {
          details: payload,
          success: (res) => {
            this.list = res.status;
            console.log(this.list);
            console.log("From log Type", this.list);
            if (this.list === 201) {
              this.$router.push("/supportengineer");
            } else {
              this.$router.push("/adminhome");
            }
            this.$toasted.success("login success", { duration: 500 });

            localStorage.setItem("emailId", this.email);
          },
        })
        .catch((error) => {
          this.$toasted.error("login failed", {
            duration: 1000,
            position: "top-center",
          });
          console.log(error);
        });
      // this.$store.dispatch("SUPPORT_DETAILS", { sid: this.getLoginState.data });
      // location.reload();
      // alert("Login Failed");
    },
  },
};
</script>

<style scoped>
.container {
  display: flex;
  justify-content: center;
}

.login {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  width: 350px;
  margin-right: 50px;
  padding: 30px 30px;
  border: 2px solid black;
  margin: 30px;
}
input {
  width: 300px;
  height: 40px;
}

.button-19 {
  appearance: button;
  background-color: #1899d6;
  border: solid transparent;
  border-radius: 16px;
  border-width: 0 0 4px;
  box-sizing: border-box;
  color: #ffffff;
  cursor: pointer;
  display: inline-block;
  font-family: din-round, sans-serif;
  font-size: 15px;
  font-weight: 700;
  letter-spacing: 0.8px;
  line-height: 20px;
  margin: 0;
  outline: none;
  overflow: visible;
  padding: 13px 16px;
  text-align: center;
  text-transform: uppercase;
  touch-action: manipulation;
  transform: translateZ(0);
  transition: filter 0.2s;
  user-select: none;
  -webkit-user-select: none;
  vertical-align: middle;
  white-space: nowrap;
  width: 200px;
}

.button-19:after {
  background-clip: padding-box;
  background-color: #1cb0f6;
  border: solid transparent;
  border-radius: 16px;
  border-width: 0 0 4px;
  bottom: -4px;
  content: "";
  left: 0;
  position: absolute;
  right: 0;
  top: 0;
  z-index: -1;
}

.button-19:main,
.button-19:focus {
  user-select: auto;
}

.button-19:hover:not(:disabled) {
  filter: brightness(1.1);
  -webkit-filter: brightness(1.1);
}

.button-19:disabled {
  cursor: auto;
}

#Login {
  width: 300px;
}
</style>
