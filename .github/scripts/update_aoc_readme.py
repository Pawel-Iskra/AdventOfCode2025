import os
import urllib.request

session = "".join(os.environ["AOC_SESSION"].split())

print("Session length:", len(session))
print("Session starts with:", session[:3])

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