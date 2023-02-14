<template>
  <nav class="navbar">
    <div class="left-section">
      <h4>CRM</h4>
    </div>
    <div v-if="getLoginBool" class="right">
      <!-- <p>{{ getSupportEngineerDetails.supportName }}</p> -->
      <button @click="logout" class="but">LogOut</button>
    </div>
  </nav>
</template>
<script>
import { mapActions, mapGetters } from "vuex";
export default {
  name: "NavBar",
  data() {
    return {
      logType: "Admin",
    };
  },
  computed: {
    ...mapGetters(["getLoginBool", "getSupportEnginnerDetails"]),
  },
  created() {
    this.$store.dispatch("SUPPORT_DETAILS", {
      sid: localStorage.getItem("id"),
    });
  },
  methods: {
    ...mapActions(["RESET_LOGIN", "SUPPORT_DETAILS"]),
    logout() {
      this.$store.dispatch("RESET_LOGIN", {
        eid: localStorage.getItem("emailId"),
      });
      this.$router.push("/");
    },
  },
};
</script>
<style scoped>
.navbar {
  background-color: #1899d6;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  /* font-family: Georgia, "Times New Roman", Times, serif; */
  padding: 0 20px;
  top: 0px;
  position: sticky;
  z-index: 999;
}

.user-avatar {
  height: 40px;
  width: 40px;
  border-radius: 50%;
  cursor: pointer;
}
.but {
  border-radius: 4px;
  box-shadow: 0 3px 20px rgba(0, 0, 0, 0.239);
}
</style>
