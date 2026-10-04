import os
import urllib.request

session = os.environ["AOC_SESSION"]

print("Session length:", len(session))
print("Contains whitespace:", any(c.isspace() for c in session))
print("Session starts:", session[:10])
print("Session ends:", session[-10:])
print("<title>Advent of Code" in html)
print("Log In" in html)

year = 2025
url = f"https://adventofcode.com/{year}/leaderboard/self"

request = urllib.request.Request(
    url,
    headers={
        "Cookie": f"session={session}",
        "User-Agent": "AoC README updater"
    }
)

with urllib.request.urlopen(request) as response:
    data = response.read().decode("utf-8")

print(f"Downloaded {len(data)} bytes")
print(data[:200])