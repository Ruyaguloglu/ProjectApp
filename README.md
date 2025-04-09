# 🛍️ E-Commerce Android App

This is a simple, modern e-commerce mobile application built with Android Studio. The application includes user authentication, product listing, shopping cart functionality, and onboarding screens to guide the user experience.

-------------------------------------------------------------

## 🚀 Features

### 📱 Onboarding Screens
- The app opens with 3 onboarding screens that describe the main features and purpose of the application.
- These screens highlight fast product delivery, secure service, and easy shopping.
- At the end of the onboarding, the user can tap the **Start** button to proceed.

### 👤 Authentication
- New users can sign up using **Name, Email, and Password**.
- Existing users can sign in using just **Email and Password**.
- Authentication is handled securely using **Firebase Authentication**.

### 🏠 Home Screen
- Built using `HomeFragment`, this screen includes:
  - **Toolbar** with an options menu (shopping cart navigation).
  - A **sliding banner** at the top (auto-moving ads).
  - Three horizontal `RecyclerViews` for:
    - **Categories**
    - **Popular Products**
    - **New Products**

### 🛒 Shopping Cart
- Products can be added to the cart by tapping **Add to Cart**.
- The cart page (`CartActivity`) shows all selected items and calculates the **total price**.
- Users can add **multiple quantities** of the same product.

### 📦 Product Details
- Tapping any product opens a detailed screen with:
  - Product description
  - **Buy Now** and **Add to Cart** buttons

---

## 🔧 Technologies Used

- **Java**
- **Android Studio**
- **Firebase Authentication** (user login & registration)
- **Firebase Cloud Firestore** (storing and fetching product data)

---

## 📷 Screenshots
 
![Onboarding](Project_Photos/screenshot_onboarding1.png)

![Onboarding2](Project_Photos/screenshot_onboarding2.png) 

![Onboarding3](Project_Photos/screenshot_onboarding3.png)

![SignUp](Project_Photos/screenshot_Sign_Up.png)

![HomePage](Project_Photos/screenshot_Home_Page.png)

![Product](Project_Photos/screenshot_product.png)

![AllItems](Project_Photos/screenshot_All_Items.png)

![AddToCart](Project_Photos/screenshot_Add_To_Cart.png)










