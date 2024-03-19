import * as React from "react";
// import ResponsiveDrawer from "./dashboard/ReponsiveDrawer";
import {
  Container,
  Grid,
  Typography,
  TextField,
  Button,
  Link,
  Toolbar,
} from "@mui/material";
import Box from "@mui/material/Box";
import PropTypes from "prop-types";
import AppBar from "@mui/material/AppBar";
import { styled, createTheme, ThemeProvider } from "@mui/material/styles";

import CssBaseline from "@mui/material/CssBaseline";
import Divider from "@mui/material/Divider";
import Drawer from "@mui/material/Drawer";
import IconButton from "@mui/material/IconButton";
import InboxIcon from "@mui/icons-material/MoveToInbox";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemIcon from "@mui/material/ListItemIcon";
import ListItemText from "@mui/material/ListItemText";
import MailIcon from "@mui/icons-material/Mail";
import MenuIcon from "@mui/icons-material/Menu";
import Paper from "@mui/material/Paper";
import Chart from "./dashboard/Chart";
import Deposits from "./dashboard/Deposits";
import Orders from "./dashboard/Orders";

import { useNavigate } from "react-router-dom";

function IssueMgmt(props) {
  const { window } = props;

  const drawerWidth = 240;
  const [mobileOpen, setMobileOpen] = React.useState(false);
  const handleDrawerToggle = () => {
    setMobileOpen(!mobileOpen);
  };
  const container =
    window !== undefined ? () => window().document.body : undefined;

  const onButtonClick = (event) => {
    console.log(event.currentTarget.getAttribute("name"));
    // console.log(event.currentTarget.value);
    // console.log(event.currentTarget.key);
    // const name = event.currentTarget.getAttribute('name');
    // if(name === 'Issue Management'){
    //     console.log("Issue Management");
    //     // navigate('/login'); //잘 가는데 issue로 안 가네
    //     navigate('/issue'); //잘 가는데 issue로 안 가네
  };

  function Copyright(props) {
    return (
      <Typography
        variant="body2"
        color="text.secondary"
        align="center"
        {...props}
      >
        {"Copyright © "}
        <Link color="inherit" href="https://mui.com/">
          Your Website
        </Link>{" "}
        {new Date().getFullYear()}
        {"."}
      </Typography>
    );
  }

  const defaultTheme = createTheme();

  const drawer = (
    <div>
      <Toolbar />

      <Divider />
      <List>
        {/* {['Inbox', 'Starred', 'Send email', 'Drafts'].map((text, index) => ( */}
        {[
          "Issue Management",
          "Sales View",
          "Contact Management",
          "Certificates Management",
        ].map((text, index) => (
          <ListItem key={index} disablePadding>
            {/* <ListItem key={text} disablePadding></ListItem> */}
            <ListItemButton name={text} onClick={onButtonClick}>
              {" "}
              {/**클릭하는 칸 : onClick을 써볼까*/}
              <ListItemIcon>
                {index % 2 === 0 ? <InboxIcon /> : <MailIcon />}
              </ListItemIcon>
              <ListItemText primary={text} /> {/** 여기가 글씨 나오는 칸 */}
            </ListItemButton>
          </ListItem>
        ))}
      </List>
      <Divider />
      <List>
        {["All mail", "Trash", "Spam"].map((text, index) => (
          <ListItem key={text} disablePadding>
            <ListItemButton>
              <ListItemIcon>
                {index % 2 === 0 ? <InboxIcon /> : <MailIcon />}
              </ListItemIcon>
              <ListItemText primary={text} />
            </ListItemButton>
          </ListItem>
        ))}
      </List>
    </div>
  );

  const handleSubmit = (event) => {};

  return (
    <ThemeProvider theme={defaultTheme}>
      <>
        <Box sx={{ display: "flex" }}>
          <CssBaseline />

          {/* <Drawer
        container={container}
        variant="temporary"
        open={mobileOpen}
        onClose={handleDrawerToggle}
        ModalProps={{
          keepMounted: true, // Better open performance on mobile.
        }}
        sx={{
          display: { xs: "block", sm: "none" },
          "& .MuiDrawer-paper": { boxSizing: "border-box", width: drawerWidth },
        }}
      ></Drawer> */}

          {/* 화면 좌측에 위치하는 Drawer 프레임 */}
          <Drawer
            variant="permanent"
            sx={{
              display: { xs: "none", sm: "block" },
              "& .MuiDrawer-paper": {
                boxSizing: "border-box",
                width: drawerWidth,
              },
            }}
            open
          >
            {/* Drawer 프레임 내부에 들어갈 내용 */}
            {drawer}
          </Drawer>
          {/* 여기까지가 Drawer */}
        </Box>
      </>
    </ThemeProvider>
  );
}

export default IssueMgmt;
