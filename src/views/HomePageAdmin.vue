<template>
  <div class="container">
    <div class="inner">
      <div>
        <button @click="LeadHandler" class="button-19">Leads</button>
      </div>
      <div>
        <button @click="TicketHandler" class="button-19">Tickets</button>
      </div>
      <div id="drop-down-parent">
        <button @click="SupportHandler" class="button-19">
          Create Support
        </button>
      </div>
    </div>
    <div class="inner2">
      <h1>Customer Relationship Management Portal</h1>
    </div>
  </div>
</template>

<script>
export default {
  name: "HomePageAdmin",
  data() {
    return {};
  },
  methods: {
    LeadHandler() {
      this.$router.push("/leads");
    },
    TicketHandler() {
      this.$router.push("/tickets");
    },
    SupportHandler() {
      this.$router.push("/createsupport");
    },
  },
};
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  background-color: white;
  height: 100vh;
}
.inner {
  display: flex;
  flex-direction: column;
  /* justify-content: space-around; */
}
.inner2 {
  display: flex;
  justify-content: center;
  align-items: center;
}

/* CSS */
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
  margin-top: 100px;
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
.drop-down-parent > div.drop-down {
  visibility: hidden;
}
.drop-down-parent:hover {
  /* visibility: hidden; */
  visibility: visible;
}
.button-19:disabled {
  cursor: auto;
}
</style>
