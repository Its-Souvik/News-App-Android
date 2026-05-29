# News App Android

A modern Android News Application built using Java and NewsAPI. The app allows users to browse the latest news headlines, search for news articles, and view detailed news content.

## Features

* Browse latest news headlines
* Search news by keyword
* Category-based news filtering

  * Business
  * Entertainment
  * General
  * Health
  * Science
  * Sports
  * Technology
* Detailed article view
* RecyclerView-based news feed
* Modern Discover-style UI
* Image loading for news articles

## Tech Stack

* Java
* Android Studio
* RecyclerView
* Retrofit
* Gson Converter
* NewsAPI
* CardView

## Screenshots

### Home Screen
![Home Screen](home_screen.png)

### Search Screen
![Search Screen](search_screen.png)

### Details Screen
![Details Screen](details_screen.png)

## Project Structure

* MainActivity
* DetailsActivity
* CustomAdapter
* CustomViewHolder
* RequestManager
* NewsApiResponse
* NewsHeadlines
* Source

## Installation

1. Clone the repository

```bash
git clone https://github.com/Its-Souvik/News-App-Android.git
```

2. Open in Android Studio

3. Add your NewsAPI key in:

```xml
res/values/strings.xml
```

```xml
<string name="api_key">YOUR_API_KEY_HERE</string>
```

4. Run the application

## Future Improvements

* Dark Mode
* Bookmark Articles
* Share News
* Offline Caching
* Pagination
* Better Category UI

## Author

Souvik Ghosh

GitHub: https://github.com/Its-Souvik
